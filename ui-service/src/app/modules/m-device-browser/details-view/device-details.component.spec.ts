import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MaterialUiModule } from "@shared/modules/material-ui.module";

import { DeviceBrowserDetailsPanelComponent } from './device-details.component';

describe('DeviceBrowserDetailsPanelComponent', () => {
  let component: DeviceBrowserDetailsPanelComponent;
  let fixture: ComponentFixture<DeviceBrowserDetailsPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialUiModule ],
      declarations: [ DeviceBrowserDetailsPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceBrowserDetailsPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
