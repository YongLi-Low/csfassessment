import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css']
})
export class View2Component implements OnInit{

  bundleId = ""

  constructor(private activatedRoute: ActivatedRoute) {}
  
  ngOnInit(): void {
    this.bundleId = this.activatedRoute.snapshot.params['bundleId']
  }

  getData(bundleId: string) {
    
  }

}
