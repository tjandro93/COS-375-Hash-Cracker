import {Component} from "@angular/core";
import {ApiError} from "../model/api-error.model";
import {Secret} from "../model/secret.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SecretRepository} from "../model/secret.repository";

/*
Component to be used to crack a hash and display the associated password
 */

@Component({
  selector: 'hash-crack',
  templateUrl: 'hash-crack.component.html',
  styleUrls: ['../../../node_modules/bootstrap/dist/css/bootstrap.min.css']
})
export class HashCrackComponent {
  passwordFound : boolean = false;
  hasApiError: boolean = false;
  secret: Secret = new Secret();
  error: ApiError = new ApiError();
  crackForm: FormGroup;
  hashValueAlert: string = "LM Hashes can only be 32 digit hexadecimal numbers";

  constructor(private repository: SecretRepository, private formBuilder: FormBuilder){
    this.crackForm = formBuilder.group({
      'hashtext' : [null, Validators.compose(
        [Validators.required,
                  Validators.minLength(32),
                  Validators.maxLength(32),
                  Validators.pattern('[\\d|a-f|A-F]{32}')]
      )]
    })
  }

  submitForm(form){
    this.secret = this.repository.getSecretByHash(form.hashtext);
    this.passwordFound = true;
  }
}
