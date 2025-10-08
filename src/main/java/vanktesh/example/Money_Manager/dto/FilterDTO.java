package vanktesh.example.Money_Manager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilterDTO {
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String keyword;
    private String sortField; //sort on basis of date, amount, name
    private String sortOrder;// desc or asc
}
