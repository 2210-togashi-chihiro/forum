package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.entity.Comment;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ForumController {
    @Autowired
    ReportService reportService;
    @Autowired
    CommentService commentService;
    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();

        // 投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport();
        List<CommentForm> commentData = commentService.findAllComment();

        // 画面遷移先を指定
        mav.setViewName("/top");

        // form用の空のentityを準備
        CommentForm commentForm = new CommentForm();
        // 返信用の空のFormを保管
        mav.addObject("formModel", commentForm);

        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        mav.addObject("comments", commentData);

        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") ReportForm reportForm){
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id){

        // キーを引数にserviceを呼出し
        reportService.deleteReport(id);

        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿編集画面　表示処理
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editContent(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView();

        // 投稿を1件取得
        ReportForm content = reportService.editContent(id);

        // 画面遷移先を指定
        mav.setViewName("/edit");
        // 投稿データオブジェクトを保管
        mav.addObject("content", content);
        return mav;
    }

    /*
     * 投稿編集処理
     */
    @PutMapping("/update/{id}")
    public ModelAndView updateContent(@PathVariable Integer id, @ModelAttribute("content") ReportForm report){

        // UrlParameterのidを更新するentityにセット
        report.setId(id);

        // 投稿をテーブルに格納
        reportService.saveReport(report);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 返信処理
     */
    @PostMapping("/addComment")
    public ModelAndView addComment(@ModelAttribute("formModel") CommentForm commentForm){
        // 投稿をテーブルに格納
        commentService.saveComment(commentForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 返信編集画面　表示処理
     */
    @GetMapping("/editComment/{id}")
    public ModelAndView editComment(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView();

        // 投稿を1件取得
        CommentForm comment = commentService.editComment(id);

        // 画面遷移先を指定
        mav.setViewName("/editComment");
        // 投稿データオブジェクトを保管
        mav.addObject("comment", comment);
        return mav;
    }

    /*
     * 返信編集処理
     */
    @PutMapping("/updateComment/{id}")
    public ModelAndView updateComment(@PathVariable Integer id, @ModelAttribute("comment") CommentForm comment){

        // UrlParameterのidを更新するentityにセット
        comment.setId(id);

        // 投稿をテーブルに格納
        commentService.saveComment(comment);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/deleteComment/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id){

        // キーを引数にserviceを呼出し
        commentService.deleteComment(id);

        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}
