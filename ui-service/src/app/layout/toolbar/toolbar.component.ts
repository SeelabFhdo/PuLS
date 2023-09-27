import { Component, Input, OnInit } from '@angular/core';
import { TranslocoService } from '@ngneat/transloco';

@Component({
    selector: 'toolbar-component',
    templateUrl: './toolbar.component.html',
    styleUrls: ['./toolbar.component.scss'],
  })
  export class ToolbarComponent implements OnInit {

    private _logoSrc: string;
    private _logoAlt: string;
    private _appTitle: string;
    private _config: any;

    @Input()
    set logoSrc(logoSrc: string) {
      this._logoSrc = logoSrc;
    }
    get logoSrc(): string {
      return this._logoSrc;
    }

    @Input()
    set logoAltLabel(logoAltLabel: string) {
      this._logoAlt = logoAltLabel;
    }
    get logoAltLabel(): string {
      return this._logoAlt;
    }

    @Input()
    set appTitle(appTitle: string) {
      this._appTitle = appTitle;
    }
    get appTitle(): string {
      return this._appTitle;
    }

    @Input()
    set config(config: any) {
      this._config = config;
    }
    get config(): any {
      return this._config;
    }

    constructor(private translocoService: TranslocoService) {}

    get activeLang(): string {
      return this.translocoService.getActiveLang().toUpperCase();
    }

    ngOnInit() {}
}