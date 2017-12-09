
import {Injectable} from "@angular/core";
import {RestDataSource} from "./rest.datasource";
import {RestResponse} from "./rest-response.model";
import {Secret} from "./secret.model";
import {LmHash} from "./lm-hash.model";
import {Metadata} from "./metadata.model";

@Injectable()
export class SecretRepository{

  private secret : Secret = new Secret(0, "", new LmHash(0, "", new Metadata(0,0,0,0,false)));
  private secretArr: Secret[];
  constructor(private dataSource: RestDataSource){}

  getAllSecrets() : Secret[] {
    this.dataSource.getAllSecrets().subscribe(response => this.secretArr = response);
    return this.secretArr;
  }

  getSecretByPlaintext(plaintext: string): Secret{
    this.dataSource.getSecretByPlaintext(plaintext).subscribe(response => this.secret = response);
    return this.secret;
  }

  getSecretByHash(hash: string) : Secret {
    this.dataSource.getSecretByHash(hash).subscribe(response => this.secret = response);
    return this.secret;
  }

  deleteSecret(plaintext: string) : Secret{
    this.dataSource.deleteSecret(plaintext).subscribe(response => this.secret = response);
    return this.secret;
  }

  createSecret(plaintext: string) : Secret {
    this.dataSource.createSecret(plaintext).subscribe(response => this.secret = response);
    return this.secret;
  }
}
