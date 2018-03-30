package sankemao.gankio.model.bean.pins;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2017/11/8.23:45
 * Author:jin
 * Email:210980059@qq.com
 */
public class PinsBoardEntity implements Serializable{

    /**
     * board_id : 13102151
     * user_id : 6231631
     * title : 版式设计
     * description :
     * category_id : design
     * seq : 7
     * pin_count : 518
     * follow_count : 386
     * like_count : 11
     * created_at : 1375963236
     * updated_at : 1510154414
     * deleting : 0
     * is_private : 0
     * extra : {"cover":{"pin_id":"293264482"}}
     */

    private int board_id;
    private int user_id;
    private String title;
    private String description;
    private String category_id;
    private int seq;
    private int pin_count;
    private int follow_count;
    private int like_count;
    private int created_at;
    private int updated_at;
    private int deleting;
    private int is_private;
    private ExtraBean extra;

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getPin_count() {
        return pin_count;
    }

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public int getDeleting() {
        return deleting;
    }

    public void setDeleting(int deleting) {
        this.deleting = deleting;
    }

    public int getIs_private() {
        return is_private;
    }

    public void setIs_private(int is_private) {
        this.is_private = is_private;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public static class ExtraBean implements Serializable{
        /**
         * cover : {"pin_id":"293264482"}
         */

        private CoverBean cover;

        public CoverBean getCover() {
            return cover;
        }

        public void setCover(CoverBean cover) {
            this.cover = cover;
        }

        public static class CoverBean implements Serializable{
            /**
             * pin_id : 293264482
             */

            private String pin_id;

            public String getPin_id() {
                return pin_id;
            }

            public void setPin_id(String pin_id) {
                this.pin_id = pin_id;
            }
        }
    }
}
