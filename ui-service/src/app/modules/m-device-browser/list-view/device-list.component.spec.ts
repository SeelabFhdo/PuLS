import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MaterialUiModule } from "@shared/modules/material-ui.module";

import { DeviceBrowserListComponent } from './device-list.component';

describe('DeviceBrowserListComponent', () => {
  let component: DeviceBrowserListComponent;
  let fixture: ComponentFixture<DeviceBrowserListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialUiModule ],
      declarations: [ DeviceBrowserListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceBrowserListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
