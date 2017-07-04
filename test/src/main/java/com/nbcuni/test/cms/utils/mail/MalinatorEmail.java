package com.nbcuni.test.cms.utils.mail;

import com.google.gson.annotations.SerializedName;

public class MalinatorEmail {

    @SerializedName("seconds_ago")
    private int secondsAgo;

    @SerializedName("id")
    private String id;

    @SerializedName("to")
    private String to;

    @SerializedName("time")
    private long time;

    @SerializedName("subject")
    private String subject;

    @SerializedName("fromfull")
    private String fromFull;

    @SerializedName("been_read")
    private boolean isRead;

    @SerializedName("from")
    private String from;

    @SerializedName("ip")
    private String ip;

    public int getSecondsAgo() {
        return secondsAgo;
    }

    public void setSecondsAgo(int secondsAgo) {
        this.secondsAgo = secondsAgo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFromFull() {
        return fromFull;
    }

    public void setFromFull(String fromFull) {
        this.fromFull = fromFull;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result
                + ((fromFull == null) ? 0 : fromFull.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((ip == null) ? 0 : ip.hashCode());
        result = prime * result + (isRead ? 1231 : 1237);
        result = prime * result + secondsAgo;
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + (int) (time ^ (time >>> 32));
        result = prime * result + ((to == null) ? 0 : to.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MalinatorEmail other = (MalinatorEmail) obj;
        if (from == null) {
            if (other.from != null)
                return false;
        } else if (!from.equals(other.from))
            return false;
        if (fromFull == null) {
            if (other.fromFull != null)
                return false;
        } else if (!fromFull.equals(other.fromFull))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (ip == null) {
            if (other.ip != null)
                return false;
        } else if (!ip.equals(other.ip))
            return false;
        if (isRead != other.isRead)
            return false;
        if (secondsAgo != other.secondsAgo)
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        if (time != other.time)
            return false;
        if (to == null) {
            if (other.to != null)
                return false;
        } else if (!to.equals(other.to))
            return false;
        return true;
    }

}
