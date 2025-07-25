package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport(String startDate, String endDate) throws ParseException {

        //時刻をset(未指定の場合デフォルト値を設定)
        if(hasText(startDate)) {
            startDate += " 00:00:00";
        } else {
            startDate = "2020-01-01 00:00:00";
        }
        if (hasText(endDate)){
            endDate += " 23:59:59";
        } else {
            Date nowDate = new Date();
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            endDate = sdFormat.format(nowDate);
        }

        //Stringに型変換
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = sdFormat.parse(startDate);
        Date end = sdFormat.parse(endDate);

        List<Report> results = reportRepository.findByCreatedDateBetweenOrderByUpdatedDateDesc(start, end);
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }


    /*
     * DBから取得したデータをFormに設定
     */
    private List<ReportForm> setReportForm(List<Report> results) {
        List<ReportForm> reports = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ReportForm report = new ReportForm();
            Report result = results.get(i);
            report.setId(result.getId());
            report.setContent(result.getContent());
            report.setCreatedDate(result.getCreatedDate());
            report.setUpdatedDate(result.getUpdatedDate());
            reports.add(report);
        }
        return reports;
    }

    /*
     * レコード追加
     */
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);

        //save…キーが存在する場合は更新、存在しない場合は登録
        reportRepository.save(saveReport);
    }

    /*
     * レコード削除
     */
    public void deleteReport(int id) {
        //deleteById…キーに該当するレコードを削除
        reportRepository.deleteById(id);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report setReportEntity(ReportForm reqReport) {
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        report.setCreatedDate(reqReport.getCreatedDate());
        report.setUpdatedDate(reqReport.getUpdatedDate());
        return report;
    }

    /*
     * レコード1件選択
     */
    public ReportForm singleContent(Integer id) {
        //deleteById…キーに該当するレコードを削除
        List<Report> results = new ArrayList<>();

        //findByIdはOptional<Report>で返る…Select結果が0件の時nullを返すよう[.orElse(null)]を付けてあげる
        results.add((Report) reportRepository.findById(id).orElse(null));

        List<ReportForm> report = setReportForm(results);
        return report.get(0);
    }

    /*
     * 更新日時の更新
     */
    public void updateReport(Integer id) throws ParseException {
        //JPAのsave(entity)にかけるため、idをキーにして1レコード取得
        ReportForm saveReportForm = singleContent(id);
        Report saveReport = setReportEntity(saveReportForm);

        //更新日時(現在時刻)を取得
        Date nowDate = new Date();
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdFormat.format(nowDate);
        Date date = sdFormat.parse(strDate);

        //更新日時(現在時刻)を取得したレコード(Report)にセット
        saveReport.setUpdatedDate(date);

        //save…キーが存在する場合は更新、存在しない場合は登録
        reportRepository.save(saveReport);
    }
}
