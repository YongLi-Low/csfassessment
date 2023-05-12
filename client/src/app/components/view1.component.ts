import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { UploadService } from '../services/upload.service';
import { UploadResult } from '../model/upload-result';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit {

  @ViewChild('file')
  zipFile!: ElementRef;

  form!: FormGroup
  uploadResult!: UploadResult

  constructor(private fb: FormBuilder, private router: Router, private uploadSvc: UploadService) { }

  ngOnInit(): void {
      this.form = this.fb.group({
        name: this.fb.control('', [Validators.required]),
        title: this.fb.control('', [Validators.required]),
        comments: this.fb.control(''),
        zipFile: this.fb.control('', [Validators.required])
      })
  }

  upload() {
    const formVal = this.form.value
    this.uploadSvc.upload(formVal, this.zipFile)
      .then((result) => {
        this.uploadResult = result
        console.log("success upload")
      })
      .catch(error => console.log(error))
  }
}
