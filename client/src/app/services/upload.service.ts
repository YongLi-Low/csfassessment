import { HttpClient } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { UploadResult } from '../model/upload-result';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private httpClient: HttpClient) { }

  upload(form: any, zipFile: ElementRef) {
    const formData = new FormData();

    formData.set("name", form['name']);
    formData.set("title", form['title']);
    formData.set("comments", form['comments']);
    formData.set("zipFile", zipFile.nativeElement.files[0]);

    return lastValueFrom(this.httpClient.post<UploadResult>('/upload', formData))
  }
}
