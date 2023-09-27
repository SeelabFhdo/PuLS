import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { first } from "rxjs/operators";
import { UserService } from "@shared/services/backend-data/user.service";
import { environment } from "@environment";
import { KeyCloakAuthService } from "@shared/services/backend-data/keycloak-auth.service";

@Component({
  selector: "login-component",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss", "../login.styles.scss"],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error = "";

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

    this.loginForm = this.formBuilder.group({
      email: ["", Validators.required],
      password: ["", Validators.required],
    });

    // get return url from route params or default to '/'
    this.returnUrl = this.route.snapshot.queryParams["returnUrl"] || "/";
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    //check form input validity state
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;

    this.authService.login();

    /*
    // old oauth 2 login code with manual user management

    this.authService
      .login(this.f.email.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        (authToken) => {
          // access token successfully received. Now, try to get the corresponding user
          this.userService.getByEmail(this.f.email.value).subscribe(
            (userData) => {
              // store current user
              localStorage.setItem("currentUser", JSON.stringify(userData));

              // redirect
              this.router.navigate([this.returnUrl]);
            },
            (error) => {
              this.error = error;
              this.loading = false;
            }
          );
        },
        (error) => {
          this.error = error;
          this.loading = false;
        }
      );
      */
  }

  public get platformTitle(): string {
    return this._platformTitle;
  }
}
