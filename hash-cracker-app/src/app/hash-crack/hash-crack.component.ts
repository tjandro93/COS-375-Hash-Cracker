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
  working: boolean = false;
  passwordFound : boolean = false;
  hasApiError: boolean = false;
  secret: Secret = new Secret();
  apiError: ApiError = new ApiError();
  crackForm: FormGroup;
  hashValueAlert: string = "LM Hashes can only be 32 digit hexadecimal numbers";
  buttonTextWorking: string = "Cracking Hash";
  buttonTextReady: string = "Crack Hash"

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
    this.working = true;
    this.repository.getSecretByHash(form.hashtext).subscribe(res => {
      this.secret = res
      this.passwordFound = true;
      this.hasApiError = false;
    }, err => {
      this.apiError = err.error;
      this.passwordFound = false;
      this.hasApiError = true;
    }, () =>
      {
        this.working = false;
      });
  }
}
