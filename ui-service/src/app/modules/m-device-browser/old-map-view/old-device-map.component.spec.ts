import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MaterialUiModule } from "@shared/modules/material-ui.module";

import { OldDeviceBrowserMapViewComponent } from './old-device-map.component';

// DEPRECATED

describe('DeviceBrowserMapViewComponent', () => {
  let component: OldDeviceBrowserMapViewComponent;
  let fixture: ComponentFixture<OldDeviceBrowserMapViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialUiModule ],
      declarations: [ OldDeviceBrowserMapViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OldDeviceBrowserMapViewComponent);
    component = fixture.componentInstance;
    component.title = "";
    component.initialLocation = [];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
