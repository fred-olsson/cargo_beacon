/// <reference types="@types/googlemaps" />
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  options: any;
  overlays: any[];

  constructor() { }

  ngOnInit() {
    this.options = {
      center: {lat: 61.349383, lng: 16.0963274},
      zoom: 12
    };
    this.overlays = [
      new google.maps.Marker({position: {lat: 61.349383, lng: 16.0963274}, title: 'CargoBeacon'})];
  }

  getMap(lat: number, long: number, id: string, map: any) {
    this.options = {
      center: {lat: lat, lng: long},
      zoom: 12
    };
    this.overlays = [
      new google.maps.Marker({position: {lat: lat, lng: long}, title: id})];
    console.log(this.options);
  }
}
