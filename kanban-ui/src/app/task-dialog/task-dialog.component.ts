import { Component, OnInit, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from "@angular/material";
import { Task } from "../model/task/task";
import { MatInputModule } from "@angular/material/input";
import { FormBuilder, Validators, FormGroup, FormArray } from "@angular/forms";
import { KanbanService } from "../service/kanban-service.service";
import { TaskService } from "../service/task.service";
import { Tag } from "../model/task/Tag";

@Component({
  selector: "app-task-dialog",
  templateUrl: "./task-dialog.component.html",
  styleUrls: ["./task-dialog.component.css"],
})
export class TaskDialogComponent implements OnInit {
  dialogTitle: String;
  kanbanId: String;
  task: Task;

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<TaskDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data,
    private kanbanService: KanbanService,
    private taskService: TaskService
  ) {
    this.dialogTitle = data.title;
    this.kanbanId = data.kanbanId;
    this.task = data.task;

    this.form = fb.group({
      title: [this.task.title, Validators.required],
      tags: this.fb.array([]),
      description: [this.task.description, Validators.required],
      color: [this.task.color, Validators.required],
    });

    if (this.task.tags)
      this.form.setControl("tags", this.setTags(this.task.tags));
    else this.addNewTag();
  }

  get tags() {
    return this.form.get("tags") as FormArray;
  }

  ngOnInit() {}

  save() {
    this.mapFormToTaskModel();
    if (!this.task.id) {
      this.kanbanService
        .saveNewTaskInKanban(this.kanbanId, this.task)
        .subscribe();
    } else {
      this.taskService.updateTask(this.task).subscribe();
    }
    this.dialogRef.close();
    window.location.reload();
  }

  close() {
    this.dialogRef.close();
  }

  delete() {
    this.taskService.deleteTask(this.task.id).subscribe();
    this.dialogRef.close();
    window.location.reload();
  }

  private mapFormToTaskModel(): void {
    this.task.title = this.form.get("title").value;
    this.task.tags = this.tags.value;
    this.task.description = this.form.get("description").value;
    this.task.color = this.form.get("color").value;
    this.task.status = "TODO";
  }

  addNewTag() {
    this.tags.push(this.newTag());
  }

  newTag(): FormGroup {
    return this.fb.group({
      id: [""],
      name: [""],
    });
  }

  deleteTag(index: number): void {
    this.tags.removeAt(index);
    this.tags.markAsDirty();
  }

  setTags(tags: Tag[]): FormArray {
    const formArray = new FormArray([]);

    if (tags.length > 0) {
      tags.forEach((a) => {
        formArray.push(
          this.fb.group({
            id: a.id,
            name: a.name,
          })
        );
      });
    } else {
      formArray.push(
        this.fb.group({
          id: [""],
          name: [""],
        })
      );
    }

    return formArray;
  }
}
