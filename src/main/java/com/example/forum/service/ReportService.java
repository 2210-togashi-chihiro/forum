package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport() {
        List<Report> results = reportRepository.findAll();
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
        return report;
    }

    /*
     * レコード1件選択
     */
    public ReportForm editContent(Integer id) {
        //deleteById…キーに該当するレコードを削除
        List<Report> results = new ArrayList<>();

        //findByIdはOptional<Report>で返る…Select結果が0件の時nullを返すよう[.orElse(null)]を付けてあげる
        results.add((Report) reportRepository.findById(id).orElse(null));

        List<ReportForm> reports = setReportForm(results);
        return reports.get(0);
    }



}

