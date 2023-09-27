import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { TranslocoService } from '@ngneat/transloco';
import { ConfirmDialog } from '@shared/dialogs/confirm/confirm.dialog';
import { UserService } from '@shared/services/backend-data/user.service';
import { UserItemInterface } from '@sharedInterfaces/userItem.interface';

import { ProfileManagementDialog } from '../profile-management/profile-management.dialog';

@Component({
  selector: 'app-user-management-component',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.scss'],
})
export class UserManagementComponent implements OnInit {
  private _title: string;
  private _users: any[];

  dataSource: MatTableDataSource<UserItemInterface>;
  selectionModel: SelectionModel<UserItemInterface>;

  displayedColumns: string[] = [
    'selection',
    'userIcon',
    'name',
    'email',
    'role',
    'action',
  ];

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private userService: UserService,
    private translocoService: TranslocoService
    ) {
    // init data source object
    this.dataSource = new MatTableDataSource(this._users);
  }

  ngOnInit(): void {
    this._title = 'user-management.title';

    this.route.data.subscribe(data => {
      sessionStorage.setItem('sideNavSelectionIndex', data.sideNavSelectionIndex);
    });

    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.selectionModel = new SelectionModel<UserItemInterface>(true, []);

    // load user data table on component initialization
    this.reloadUserTable();
  }

  reloadUserTable() {
    // get all users
    this.userService.getAll().subscribe(
      (users) => {
        // clear list
        this._users = [];
        // load data
        for (let i = 0; i < users.length; i++) {
          this._users.push({
            name: users[i].firstname + ' ' + users[i].lastname,
            email: users[i].email,
            roles: users[i].userRoles,
          });
        }
        // create new data source object
        this.dataSource = new MatTableDataSource(this._users);

        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      (error) => {}
    );
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  isAllSelected() {
    const numSelected = this.selectionModel.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected()
      ? this.selectionModel.clear()
      : this.dataSource.data.forEach((row) => this.selectionModel.select(row));
  }

  // open user profile dialog with registration setting
  openAddUserDialog(): void {
    const dialogRef = this.dialog.open(ProfileManagementDialog, {
      data: {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        roles: ['USER'],
        passwordEditorVisible: true,
        roleEditorVisible: true,
        dialogTitle: 'user-management.profileDialog.newTitle',
        confirmButtonText: 'user-management.profileDialog.newAccept',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (typeof result !== 'undefined') {
        const userData: any = {
          email: result.email,
          firstname: result.firstName,
          lastname: result.lastName,
          password: result.password,
          userRoles: result.roles,
        };

        // console.log(JSON.stringify(userData));

        this.userService.register(userData).then(() => {
          this.reloadUserTable();
        });
      }
    });
  }

  // open user profile dialog with edit setting
  openUserProfileDialog(userItemData: UserItemInterface): void {
    this.userService.getByEmail(userItemData.email).subscribe(
      (userData) => {
        // load initial user data on profile dialog showing up
        const dialogRef = this.dialog.open(ProfileManagementDialog, {
          data: {
            firstName: userData.firstname,
            lastName: userData.lastname,
            avatar: '',
            email: userData.email,
            roles: userData.userRoles,
            roleEditorVisible: true,
          },
        });

        //
        dialogRef.afterClosed().subscribe((result) => {
          if (typeof result !== 'undefined') {
            // save edited user data
            const userData: any = {
              email: result.email,
              firstname: result.firstName,
              lastname: result.lastName,
              password: result.password,
              userRoles: result.roles,
            };

            this.userService.update(userData).then(() => {
              this.reloadUserTable();
            });
          }

          this.selectionModel.clear();
        });
      },
      (error) => {}
    );
  }

  // open delete user dialog
  openUserDeleteConfirmDialog(userItemData: any): void {
    const dialogRef = this.dialog.open(ConfirmDialog, {
      data: {
        title: 'user-management.deleteDialog.title',
        preQuestion:
          'user-management.deleteDialog.preQuestion',
        item: userItemData.name,
        postQuestion:
          'user-management.deleteDialog.postQuestion' /*,
        userId: userItemData.id,*/,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (typeof result !== 'undefined') {
        // delete the selected user
        /*
        this.userService.deleteById(result.userId).then(() => {
          this.reloadUserTable();
        });
        */
      }

      this.selectionModel.clear();
    });
  }

  // open delete all users dialog
  openSelectedUsersDeleteConfirmDialog(): void {
    const dialogRef = this.dialog.open(ConfirmDialog, {
      data: {
        title: 'user-management.deleteAllDialog.title',
        preQuestion: 'user-management.deleteAllDialog.question',
        item: '',
        postQuestion: 'user-management.deleteDialog.postQuestion',
        selectedUsers: this.selectionModel.selected,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (typeof result !== 'undefined') {
        // delete all selected users
        for (let i = 0; i < result.selectedUsers.length; i++) {
          /*
          this.userService.deleteById(result.selectedUsers[i].id).then(() => {
            this.reloadUserTable();
          });
          */
        }
        this.selectionModel.clear();
      }
    });
  }

  get title(): string {
    return this._title;
  }

  get users(): UserItemInterface[] {
    return this._users;
  }
}
