package sankemao.gankio.data.bean.pins;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2017/11/8.23:41
 * Author:jin
 * Email:210980059@qq.com
 */
public class PinsMainEntity implements Serializable{
    /**
     * pin_id : 1387392230
     * user_id : 6231631
     * board_id : 13102151
     * file_id : 138839969
     * file : {"id":138839969,"farm":"farm1","bucket":"hbimg","key":"39c28003e2af8033e5625ceec7e77396ab3ab3291b2fba-JCPGgB","type":"image/jpeg","width":"990","height":"7172","frames":"1","colors":[],"theme":"f7f7f7"}
     * media_type : 0
     * source : zhisheji.com
     * link : http://www.zhisheji.com/yuanchuang/812728.html
     * raw_text : 高端红酒详情页_详情页_原创作品-致设计
     * text_meta : {}
     * via : 1123703740
     * via_user_id : 844104
     * original : 1123703740
     * created_at : 1510154414
     * like_count : 0
     * comment_count : 0
     * repin_count : 0
     * is_private : 0
     * orig_source : null
     * user : {}
     * board : {}
     * via_user : {}
     * tags : []
     */

    private int pin_id;
    private int user_id;
    private int board_id;
    private int file_id;


    private PinsFileEntity file;
    private int media_type;
    private String source;
    private String link;
    private String raw_text;
    private int via;
    private int via_user_id;
    private int original;
    private int created_at;
    private int like_count;
    private int comment_count;
    private int repin_count;
    private int is_private;
    private String orig_source;

    private PinsUserEntity user;
    private PinsBoardEntity board;

    public int getPin_id() {
        return pin_id;
    }

    public void setPin_id(int pin_id) {
        this.pin_id = pin_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public PinsFileEntity getFile() {
        return file;
    }

    public void setFile(PinsFileEntity file) {
        this.file = file;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRaw_text() {
        return raw_text;
    }

    public void setRaw_text(String raw_text) {
        this.raw_text = raw_text;
    }

    public int getVia() {
        return via;
    }

    public void setVia(int via) {
        this.via = via;
    }

    public int getVia_user_id() {
        return via_user_id;
    }

    public void setVia_user_id(int via_user_id) {
        this.via_user_id = via_user_id;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getRepin_count() {
        return repin_count;
    }

    public void setRepin_count(int repin_count) {
        this.repin_count = repin_count;
    }

    public int getIs_private() {
        return is_private;
    }

    public void setIs_private(int is_private) {
        this.is_private = is_private;
    }

    public String getOrig_source() {
        return orig_source;
    }

    public void setOrig_source(String orig_source) {
        this.orig_source = orig_source;
    }

    public PinsUserEntity getUser() {
        return user;
    }

    public void setUser(PinsUserEntity user) {
        this.user = user;
    }

    public PinsBoardEntity getBoard() {
        return board;
    }

    public void setBoard(PinsBoardEntity board) {
        this.board = board;
    }

}
