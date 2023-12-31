import { HttpClient } from '@angular/common/http';
import { Translation, TRANSLOCO_LOADER, TranslocoLoader } from '@ngneat/transloco';
import { Injectable, OnInit } from '@angular/core';

@Injectable({ providedIn: 'root' })
  export class TranslocoHttpLoader implements TranslocoLoader {
    constructor(private http: HttpClient) {}

    getTranslation(lang: string) {
      return this.http.get<Translation>(`/assets/i18n/${lang}.json`);
    }
}

export const translocoHttpLoader = { provide: TRANSLOCO_LOADER, useClass: TranslocoHttpLoader };
