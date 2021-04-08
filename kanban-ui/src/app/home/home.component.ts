import { Component, OnInit,ViewChild } from '@angular/core';
import { Kanban } from '../model/kanban/kanban';
import { KanbanService } from '../service/kanban-service.service';
import { MatDialog, MatDialogConfig, MatTableDataSource } from '@angular/material';
import { KanbanDialogComponent } from '../kanban-dialog/kanban-dialog.component';
//import { DeleteConfirmDialogComponent, ConfirmDialogModel} from '../shared/Delete-Confirm-Dialog/Delete-Confirm-Dialog.Component';
// import { ConfirmDialogModel, ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  kanbanList: Kanban[];
  kanban: Kanban;
  result: string = '';
  dataSource = new MatTableDataSource<Kanban>();
  displayedColumns: string[] = ["id","Title","CreatedDate","details","delete"]

  constructor(
    private kanbanService: KanbanService,
    private dialog: MatDialog
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  ngOnInit() {
    this.retrieveAllKanbanBoards();
  }

  openDialogForNewKanban(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      kanban: new Kanban()
    };
    this.dialog.open(KanbanDialogComponent, dialogConfig)
  }

  private retrieveAllKanbanBoards(): void {
    this.kanbanService.retrieveAllKanbanBoards().subscribe(

      response => {
        this.kanbanList = response;
        this.dataSource.data = response;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    )
  }

  delete(kanbanId:number) {
    this.kanbanService.deleteKanban(kanbanId).subscribe();
    window.location.reload();
  }

  confirmDelete(name: number) {
    if(confirm("Are you sure to delete "+name)) {
      this.delete(name);
    }
  }

  confirmDialog(id: number): void {
    const message = `Are you sure you want to do this?`;

    // const dialogData = new ConfirmDialogModel("Confirm Action", message,id);

    // const dialogRef = this.dialog.open(ConfirmDialogComponent, {
    //   maxWidth: "400px",
    //   data: dialogData
    // });

    // dialogRef.afterClosed().subscribe(dialogResult => {
    //   if (dialogResult)
    //     this.delete(id);
    // });
  }
}
