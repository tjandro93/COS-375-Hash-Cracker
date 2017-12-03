
import {NgModule} from "@angular/core";
import {HttpModule} from "@angular/http";
import {RestDataSource} from "./rest.datasource";
import {SecretRepository} from "./secret.repository";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  imports: [HttpClientModule],
  providers: [RestDataSource, SecretRepository]
})
export class ModelModule {}
