/// <reference types="@types/googlemaps" />
import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title_text: string;
  beaconData: Array<{ id: string, long: number, lat: number }> = [];
  options: any;
  overlays: any[];


  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.options = {
      center: { lat: 61.349383, lng: 16.0963274 },
      zoom: 12
    };
    this.overlays = [
      new google.maps.Marker({ position: { lat: 61.349383, lng: 16.0963274 }, title: 'CargoBeacon' })];
    this.getBeaconData()
      .subscribe(resp => {
        // @ts-ignore
        for (const beacon of resp) {
          const last_shipment = beacon.shipments[0].gps_data[beacon.shipments[0].gps_data.length - 1];
          this.beaconData.push({ id: beacon['beacon_id'], long: last_shipment.long, lat: last_shipment.lat });
          this.title_text = 'Beacons:';
        }
      });
  }

  getBeaconData() {
    this.title_text = 'Loading Beacons...';
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
    const payload = {
      token: 'xxxxxx', // insert token
      only_last_shipment: true,
      only_active: false
    };
    return this.http.post('https://us-central1-spartan-rhino-217719.cloudfunctions.net/cb_get_data', payload, httpOptions);
  }

  getMap(lat: number, long: number, id: string, map: any) {
    map.setCenter(new google.maps.LatLng(lat, long));
  }
}
