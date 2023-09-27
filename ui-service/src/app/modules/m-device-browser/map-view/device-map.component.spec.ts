import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MaterialUiModule } from "@shared/modules/material-ui.module";

import { DeviceBrowserMapViewComponent } from './device-map.component';

describe('DeviceBrowserMapViewComponent', () => {
  let component: DeviceBrowserMapViewComponent;
  let fixture: ComponentFixture<DeviceBrowserMapViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialUiModule ],
      declarations: [ DeviceBrowserMapViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceBrowserMapViewComponent);
    component = fixture.componentInstance;
    component.title = "";
    component.initialLocation = [];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
