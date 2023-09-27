import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MaterialUiModule } from "@shared/modules/material-ui.module";

import { DeviceBrowserDetailsPanelMonitorComponent } from './details-monitor.component';

describe('DeviceBrowserDetailsPanelMonitorComponent', () => {
  let component: DeviceBrowserDetailsPanelMonitorComponent;
  let fixture: ComponentFixture<DeviceBrowserDetailsPanelMonitorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialUiModule ],
      declarations: [ DeviceBrowserDetailsPanelMonitorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceBrowserDetailsPanelMonitorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
