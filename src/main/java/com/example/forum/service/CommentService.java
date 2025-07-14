package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
     * レコード追加
     */
    public void saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentEntity(reqComment);

        //save…キーが存在する場合は更新、存在しない場合は登録
        commentRepository.save(saveComment);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqComment) {
        Comment comment = new Comment();
        comment.setId(reqComment.getId());
        comment.setReportId(reqComment.getReportId());
        comment.setReContent(reqComment.getReContent());
        return comment;
    }

    /*
     * レコード全件取得処理
     */
    public List<CommentForm> findAllComment() {
        List<Comment> results = commentRepository.findAll();
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm comment = new CommentForm();
            Comment result = results.get(i);
            comment.setId(result.getId());
            comment.setReportId(result.getReportId());
            comment.setReContent(result.getReContent());
            comments.add(comment);
        }
        return comments;
    }

    /*
     * レコード1件選択
     */
    public CommentForm editComment(Integer id) {
        //deleteById…キーに該当するレコードを削除
        List<Comment> results = new ArrayList<>();

        //findByIdはOptional<Report>で返る…Select結果が0件の時nullを返すよう[.orElse(null)]を付けてあげる
        results.add((Comment) commentRepository.findById(id).orElse(null));

        List<CommentForm> comment = setCommentForm(results);
        return comment.get(0);
    }

    /*
     * レコード削除
     */
    public void deleteComment(int id) {
        //deleteById…キーに該当するレコードを削除
        commentRepository.deleteById(id);
    }
}
