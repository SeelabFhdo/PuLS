import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { UserService } from "@shared/services/backend-data/user.service";
import { environment } from "@environment";
import { KeyCloakAuthService } from "@shared/services/backend-data/keycloak-auth.service";

@Component({
  selector: "register-component",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss", "../login.styles.scss"],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  private _platformTitle: string = "";

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: KeyCloakAuthService,
    private userService: UserService
  ) {
    //redirect to home if already logged in
    if (this.authService.getToken()) {
      this.router.navigate(["/"]);
    }
  }

  ngOnInit(): void {
    this._platformTitle = environment.platform_title;

    this.registerForm = this.formBuilder.group({
      email: ["", Validators.required],
      firstName: ["", Validators.required],
      lastName: ["", Validators.required],
      password: ["", Validators.required],
      passwordConfirm: ["", Validators.required],
    });

    this.returnUrl = this.route.snapshot.queryParams["returnUrl"] || "/";
  }

  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    //check form input validity state
    if (this.registerForm.invalid) {
      return;
    }

    //check password equality
    if (
      this.registerForm.controls.password.value !=
      this.registerForm.controls.passwordConfirm.value
    ) {
      // TODO show message.
      return;
    }

    this.loading = true;

    // TODO 1.) register new user ...
    //this.userService.register() // currently only admins can create new users
    // 2.) feedback if user was created and redirect to login on success...
  }

  public get platformTitle(): string {
    return this._platformTitle;
  }
}
