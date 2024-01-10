import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Operation } from '../model/operation/operation';
import { Nodemcu } from '../model/nodemcu';
import { Main } from '../model/main';

const headers = new HttpHeaders({
  'Authorization': 'Bearer meu-token-de-autenticacao',
  'Content-Type': 'application/json',
});


@Injectable({
  providedIn: 'root'
})

export class OperationService {

  constructor(private http: HttpClient) { }


  get(name: string, url: string): Observable<Operation> {
    if(url == "I" || url == "i"){
      return this.http.get<Operation>("http://172.16.34.147:433/api/v1/operation/" + name)
    }else if(url == "P" || url == "p")  {
      return this.http.get<Operation>("http://172.16.34.147:433/api/v1/operation_provedor/" + name)
    }else if(url == "G" || url == "g"){
      return this.http.get<Operation>("http://172.16.34.147:433/api/v1/operation_gerenciaveis/" + name)
    }else{
      return this.http.get<Operation>("http://172.16.34.147:433/api/v1/operation_display/" + name)
    }
  }

  post(body: Nodemcu, url: string): Observable<Nodemcu> {
    if(url == "I" || url == "i"){
      return this.http.patch<Nodemcu>("http://172.16.34.147:433/api/v1/nodemcu/" + body.nameId.name, body)
    }else if(url == "P" || url == "p")  {
      return this.http.patch<Nodemcu>("http://172.16.34.147:433/api/v1/provedor/" + body.nameId.name, body)
    }else if(url == "G" || url == "g"){
      return this.http.patch<Nodemcu>("http://172.16.34.147:433/api/v1/gerenciaveis/" + body.nameId.name, body)
    }else{
      return this.http.patch<Nodemcu>("http://172.16.34.147:433/api/v1/display/" + body.nameId.name, body)
    }
  }

  getByName(name: string, url: string){
    if(url == "I" || url == "i"){
      return this.http.get<Nodemcu>("http://172.16.34.147:433/api/v1/nodemcu/" + name)
    }else if(url == "P" || url == "p")  {
      return this.http.get<Nodemcu>("http://172.16.34.147:433/api/v1/provedor/" + name)
    }else if(url == "G" || url == "g"){
      return this.http.get<Nodemcu>("http://172.16.34.147:433/api/v1/gerenciaveis/" + name)
    }else{
      return this.http.get<Nodemcu>("http://172.16.34.147:433/api/v1/display/" + name)
    }
  }

  getTCimposto( url: string): Observable<Main[]> {
    if(url == "I" || url == "i"){
      return this.http.get<Main[]>("http://172.16.34.147:433/api/v1/main")
    }else if(url == "P" || url == "p")  {
      return this.http.get<Main[]>("http://172.16.34.147:433/api/v1/main_provedor")
    }else if(url == "G" || url == "g"){
      return this.http.get<Main[]>("http://172.16.34.147:433/api/v1/main_gerenciaveis")
    }else{
      return this.http.get<Main[]>("http://172.16.34.147:433/api/v1/main_display")
    }
  }

  atualizar(name: string, tempo: number, url: string) {
    if(url == "I" || url == "i"){
      this.http.get("http://172.16.34.147:433/api/v1/nodemcu/atualizarTempo/" + name + "/" + tempo).subscribe()
    }else if(url == "P" || url == "p")  {
      this.http.get("http://172.16.34.147:433/api/v1/provedor/atualizarTempo/" + name + "/" + tempo).subscribe()
    }else if(url == "G" || url == "g"){
      this.http.get("http://172.16.34.147:433/api/v1/gerenciaveis/atualizarTempo/" + name + "/" + tempo).subscribe()
    }else{
      this.http.get("http://172.16.34.147:433/api/v1/display/atualizarTempo/" + name + "/" + tempo).subscribe()
    }
  }

  atualizarState(name: string, state: string, url: string){
    if(url == "I" || url == "i"){
      this.http.get("http://172.16.34.147:433/api/v1/nodemcu/atualizarState/" + name + "/" + state).subscribe();
    }else if(url == "P" || url == "p")  {
      this.http.get("http://172.16.34.147:433/api/v1/provedor/atualizarState/" + name + "/" + state).subscribe();
    }else if(url == "G" || url == "g"){
      this.http.get("http://172.16.34.147:433/api/v1/gerenciaveis/atualizarState/" + name + "/" + state).subscribe();
    }else{
      this.http.get("http://172.16.34.147:433/api/v1/display/atualizarState/" + name + "/" + state).subscribe();
    }
  }

}
