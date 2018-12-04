package com.github.vole.common.fs.oss.aliyun;

public class AliyunOss {

    private String bucketName;
    private String urlprefix;
    private boolean isPrivate;
    private String accessKey;
    private String endpoint;
    private String secretKey;


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getUrlprefix() {
        return urlprefix;
    }

    public void setUrlprefix(String urlprefix) {
        this.urlprefix = urlprefix;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
