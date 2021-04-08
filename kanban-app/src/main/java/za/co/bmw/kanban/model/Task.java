package za.co.bmw.kanban.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "task")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position = 1)
	private Long id;

	@ApiModelProperty(position = 2)
	private String title;

	@ApiModelProperty(position = 3)
	private String description;

	@ApiModelProperty(position = 4)
	private String color;

	@ApiModelProperty(position = 5)
	private Long kanban_id;

	@Enumerated(EnumType.STRING)
	@ApiModelProperty(position = 6)
	private TaskStatus status;

	@ApiModelProperty(position = 7)
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "task_tags", joinColumns = { @JoinColumn(name = "task_id") }, inverseJoinColumns = {
			@JoinColumn(name = "tag_id") })
	private List<Tag> tags = new ArrayList<>();

	@ApiModelProperty(position = 8)
	private LocalDateTime localDateTime;

	public Task(Long id, String title, String description, String color, Long kanban_id, TaskStatus status,
			LocalDateTime localDateTime) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.color = color;
		this.kanban_id = kanban_id;
		this.status = status;
		this.localDateTime = localDateTime;
	}
}
