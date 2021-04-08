package za.co.bmw.kanban.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    @ApiModelProperty(position = 1)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @ApiModelProperty(position = 2)
    @NotBlank(message = "Description is mandatory")
    private String description;

    @ApiModelProperty(position = 3)
    @NotBlank(message = "Color is mandatory")
    private String color;

    @ApiModelProperty(position = 4)
    private TaskStatus status;

    @ApiModelProperty(position=5)
    private List<Tag> tags = new ArrayList<>();

    @ApiModelProperty(position=6)
    private Long kanban_id;

}
