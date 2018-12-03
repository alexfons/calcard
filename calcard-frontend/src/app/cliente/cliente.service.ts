import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Cliente } from '../models/cliente.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ClienteService {

  constructor(private http: HttpClient) { }
  naive_bayes
  private apiUrl = 'http://localhost:8080/api/v1/cliente';

  public getClientes(): Observable<Array<Cliente>> {
    return this.http.get<Array<Cliente>>(this.apiUrl + '')
      .pipe(map((result: any) => { return result._embedded.cliente; }));
  }

  public deleteCliente(cliente: Cliente) {
    return this.http.delete(this.apiUrl + '/' + cliente.id);
  }

  public createCliente(cliente: Cliente) {
    return this.http.post<Cliente>(this.apiUrl + '', cliente);
  }

  public avaliaProposta(cliente: Cliente) {
    return this.http.post<Cliente>(this.apiUrl + '/avaliaProposta', cliente);
  }

  public findByCpfContaining(term: string): Observable<Array<Cliente>> {
    return this.http.get<Array<Cliente>>(this.apiUrl + `/search/findByCpfContaining?q=${term}`)
      .pipe(map((result: any) => { return result._embedded.cliente; }));
  }

}
