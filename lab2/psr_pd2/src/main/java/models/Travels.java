package models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamoDBTable(tableName = "NewTravel")
public class Travels {

    private int id;
    private String name;
    private String guide;
    private String date;
    private String attendee;

    @DynamoDBHashKey
    public int getId(){ return id; }

    public void setId(int id){ this.id = id; }

    @DynamoDBAttribute(attributeName="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName="guide")
    public void setGuide(String guide) {this.guide = guide;}

    public String getGuide() {return guide;}

    @DynamoDBAttribute(attributeName="date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @DynamoDBAttribute(attributeName="attendees")
    public String getAttende() {
        return attendee;
    }

    public void setAttendee(String attendee) {
        this.attendee = attendee;
    }

    @Override
    public String toString(){
        return "[Id " + id + "] Travel: " + name + " Guide: " + guide + " Date: " + date + " attendee: " + attendee;
    }

}
