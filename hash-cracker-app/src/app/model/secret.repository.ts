
import {Injectable} from "@angular/core";
import {RestDataSource} from "./rest.datasource";
import {RestResponse} from "./rest-response.model";
import {Secret} from "./secret.model";
import {LmHash} from "./lm-hash.model";
import {Metadata} from "./metadata.model";
import {Subscription} from "rxjs/Subscription";
import {Observable} from "rxjs/Observable";

/*
Repository service to access Secrets returned by RestDatasource
 */

@Injectable()
export class SecretRepository{

  private secret : Secret //broken here;
  private secretArr: Secret[];
  constructor(private dataSource: RestDataSource){}

  getAllSecrets() : Observable<Secret[]> {
    return this.dataSource.getAllSecrets();
  }

  getSecretByPlaintext(plaintext: string): Observable<Secret>{
    return this.dataSource.getSecretByPlaintext(plaintext);
  }

  getSecretByHash(hash: string) : Observable<Secret> {
    return this.dataSource.getSecretByHash(hash);
  }

  deleteSecret(plaintext: string) : Observable<Secret>{
    return this.dataSource.deleteSecret(plaintext);
  }

  createSecret(plaintext: string) : Observable<Secret> {
    return this.dataSource.createSecret(plaintext);
  }
}
