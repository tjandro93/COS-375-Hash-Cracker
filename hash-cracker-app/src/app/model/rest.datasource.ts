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
    return this.http.get<Secret[]>(this.secretsUrl)
      .pipe(catchError(error => {
        console.error("please god", error);

        return Observable.of({description: error.toString()});
      }));
  }

  getSecretByPlaintext(plaintext: string): Observable<Secret> {
    console.log("GET /secrets/" + plaintext);
    return this.http.get<Secret>(`${this.secretsUrl}/${plaintext}`)
      .pipe(catchError(error => {
        console.error("please god", error);
        return this.createSecret(plaintext);
        }));
  }

  getSecretByHash(hash: string): Observable<Secret> {
    console.log("GET /hashes/" + hash);
    return this.http.get<Secret>(`${this.hashesUrl}/${hash}`)
      .pipe(catchError(error => {
        console.error("please god", error);

        return Observable.of({description: error.toString()});
      }));
  }

  deleteSecret(plaintext: string): Observable<Secret> {
    console.log("DELETE /secrets/" + plaintext);
    return this.http.delete<Secret>(`secrets/${plaintext}`)
      .pipe(catchError(error => {
        console.error("please god", error);

        return Observable.of({description: error.toString()});
      }));
  }

  createSecret(plaintext: string): Observable<Secret> {
    console.log("POST /secrets/" + plaintext);
    return this.http.post<Secret>(`secrets/${plaintext}`, null)
      .pipe(catchError(error => {
        console.error("please god");
        return this.getSecretByPlaintext(plaintext)
      }));
  }


}
