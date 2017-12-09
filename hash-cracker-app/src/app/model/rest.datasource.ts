import {Injectable} from "@angular/core";
import {Http, Request, RequestMethod} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Secret} from "./secret.model";
import "rxjs/add/operator/map";
import {ApiError} from "./api-error.model";
import {RestResponse} from "./rest-response.model";
import {HttpClient} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/of";

/*
Service to make Http calls to the REST application
 */


const PROTOCOL = "http";
const PORT = 8080;

@Injectable()
export class RestDataSource {
  secretsUrl: string = "secrets";
  hashesUrl: string = "hashes";

  constructor(private http: HttpClient) {
  }

  getAllSecrets(): Observable<Secret[]> {
    console.log("GET /secrets");
    return this.http.get<Secret[]>(this.secretsUrl);
  }

  getSecretByPlaintext(plaintext: string): Observable<Secret> {
    console.log("GET /secrets/" + plaintext);
    return this.http.get<Secret>(`${this.secretsUrl}/${plaintext}`);
  }

  getSecretByHash(hash: string): Observable<Secret> {
    console.log("GET /hashes/" + hash);
    return this.http.get<Secret>(`${this.hashesUrl}/${hash}`);
  }

  deleteSecret(plaintext: string): Observable<Secret> {
    console.log("DELETE /secrets/" + plaintext);
    return this.http.delete<Secret>(`secrets/${plaintext}`);
  }

  createSecret(plaintext: string): Observable<Secret> {
    console.log("POST /secrets/" + plaintext);
    return this.http.post<Secret>(`secrets/${plaintext}`, null);
  }
}
