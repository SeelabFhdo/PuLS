import { Component, Inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UtilsService } from '@shared/services/helper/utils.service';

@Component({
  selector: "profile-management-dialog",
  templateUrl: './profile-management.dialog.html',
  styleUrls: ['./profile-management.dialog.scss'],
})
export class ProfileManagementDialog implements OnInit {
  userProfileForm: FormGroup;
  loading = false;
  submitted = false;

  userRole: string;
  roles: string[] = ['USER', 'ADMINISTRATOR'];

  roleEditorVisible: boolean;
  passwordEditorVisible: boolean;
  dialogTitle: string;
  confirmButtonText: string;

  constructor(
    public dialogRef: MatDialogRef<ProfileManagementDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private formBuilder: FormBuilder,
    private utilsService: UtilsService
  ) {}

  ngOnInit(): void {
    // load component settings or default values
    this.roleEditorVisible = this.utilsService.assignOrDefault(
      this.data.roleEditorVisible,
      false
    );

    this.passwordEditorVisible = this.utilsService.assignOrDefault(
      this.data.passwordEditorVisible,
      false
    );

    this.dialogTitle = this.utilsService.assignOrDefault(
      this.data.dialogTitle,
      'user-management.profileDialog.editTitle'
    );

    this.confirmButtonText = this.utilsService.assignOrDefault(
      this.data.confirmButtonText,
      'user-management.profileDialog.editAccept'
    );

    // prepare the profile form fields and fill them with data
    this.userProfileForm = this.formBuilder.group({
      email: [this.data.email, Validators.required],
      firstName: [this.data.firstName, Validators.required],
      lastName: [this.data.lastName, Validators.required],
    });

    // dynamically add the password controls when "passwordEditorVisible" is set
    if (this.passwordEditorVisible) {
      this.userProfileForm.addControl(
        'password',
        new FormControl('', Validators.required)
      );
      this.userProfileForm.addControl(
        'passwordConfirm',
        new FormControl('', Validators.required)
      );
    }

    // get the selected role index
    let rolesSelectionIndex = this.data.roles.includes('ADMINISTRATOR') ? 1 : 0;

    // select role radio box if item index was found
    if (rolesSelectionIndex > -1 && rolesSelectionIndex < this.roles.length) {
      this.userRole = this.roles[rolesSelectionIndex];
    }
  }

  get f() {
    return this.userProfileForm.controls;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit() {
    this.submitted = true;

    // check form input validity state and if userRole is in undefined state
    if (this.userProfileForm.invalid || !this.userRole) {
      // TODO set error flag.
      return;
    }

    this.loading = true;

    // update values from text fields
    this.data.email = this.f.email.value;
    this.data.firstName = this.f.firstName.value;
    this.data.lastName = this.f.lastName.value;

    // quick and dirty solution to map from the single selection of a role
    // to the role list of the backend.
    // TODO: Change this block later!
    if (this.userRole === 'ADMINISTRATOR') {
      this.data.roles = ['USER', 'ADMINISTRATOR'];
    } else {
      this.data.roles = ['USER'];
    }

    if (this.passwordEditorVisible) {
      this.data.password = this.f.password.value;
    } else {
      this.data.password = null;
    }
  }
}
