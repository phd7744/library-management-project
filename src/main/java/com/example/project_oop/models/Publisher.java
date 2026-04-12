package com.example.project_oop.models;

public class Publisher {
    private int publisherId;
    private String publisherName;
    private String publisherAddress;

    public Publisher() {
    }

    public Publisher(int publisherId, String publisherName, String publisherAddress) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.publisherAddress = publisherAddress;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherAddress() {
        return publisherAddress;
    }

    public void setPublisherAddress(String publisherAddress) {
        this.publisherAddress = publisherAddress;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "publisherId=" + publisherId +
                ", publisherName='" + publisherName + '\'' +
                ", publisherAddress='" + publisherAddress + '\'' +
                '}';
    }
}
