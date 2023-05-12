import { HttpClient } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { UploadResult } from '../model/upload-result';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private httpClient: HttpClient, private router: Router) { }

  upload(form: any, zipFile: ElementRef) {
    const formData = new FormData();

    formData.set("name", form['name']);
    formData.set("title", form['title']);
    formData.set("comments", form['comments']);
    formData.set("zipFile", zipFile.nativeElement.files[0]);

    return lastValueFrom(this.httpClient.post<string>('/api/upload', formData))
    
    // this.httpClient.post<UploadResult>('/api/upload', formData)
    // .subscribe((response: any) => {console.log(response)})
    // lastValueFrom(this.httpClient.post<UploadResult>('/api/upload', formData))
  }
}
