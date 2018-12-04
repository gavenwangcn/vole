package com.github.vole.message.template;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 钉钉消息模板
 */
@Data
@ToString
public class DingTalkMsgTemplate implements Serializable {
    private String msgtype;
    private TextBean text;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public TextBean getText() {
        return text;
    }

    public void setText(TextBean text) {
        this.text = text;
    }

    public static class TextBean {


        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
