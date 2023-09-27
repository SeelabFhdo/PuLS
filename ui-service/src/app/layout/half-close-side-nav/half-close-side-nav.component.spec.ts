import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MaterialUiModule } from "@shared/modules/material-ui.module";

import { HalfCloseSideNavComponent } from './half-close-side-nav.component';

describe('HalfCloseSideNavComponent', () => {
  let component: HalfCloseSideNavComponent;
  let fixture: ComponentFixture<HalfCloseSideNavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialUiModule ],
      declarations: [ HalfCloseSideNavComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HalfCloseSideNavComponent);
    component = fixture.componentInstance;
    component.config = {
      preSelectIndex: -1,
      isAlwaysExpanded: false,
      isOpened: false,
      autoExpand: false,
      currentUserRoles: [],
      items: []
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
