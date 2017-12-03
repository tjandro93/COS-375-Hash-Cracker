import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {HashCrackModule} from "./hash-crack/hash-crack.module";
import {HashGenModule} from "./hash-gen/hash-gen.module";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule, HashCrackModule, HashGenModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
