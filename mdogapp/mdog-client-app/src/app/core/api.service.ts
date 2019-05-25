import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserDto } from "../model/user.dto";
import { UserDetails } from "../model/user.details";
import { Observable } from "rxjs/index";
import { ClientResponseBean } from "../model/client.responsebean";

@Injectable()
export class ApiService {

  constructor(private http: HttpClient) { }
  baseUrl: string = 'http://localhost:8080/';

  login(loginPayload) : Observable<ClientResponseBean> {
    return this.http.post<ClientResponseBean>('/mdogapp/home/v1/userLogin', loginPayload);
  }
}
