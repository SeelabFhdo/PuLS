import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MaterialUiModule } from "@shared/modules/material-ui.module";

import { ToolbarComponent } from './toolbar.component';

describe('ToolbarComponent', () => {
  let component: ToolbarComponent;
  let fixture: ComponentFixture<ToolbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialUiModule ],
      declarations: [ ToolbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ToolbarComponent);
    component = fixture.componentInstance;
    
    component.logoSrc = "";
    component.appTitle = "";
    component.config = {
      userMenu: {
        currentUserData: "",
        buttons: []
      }
    };

    fixture.detectChanges();

  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
