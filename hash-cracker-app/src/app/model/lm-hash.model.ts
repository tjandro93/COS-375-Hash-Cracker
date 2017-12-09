import {Metadata} from "./metadata.model";

/*
Model of an LM Hash exactly as it may be returned from a REST call
 */

export class LmHash {
  constructor(public id?: number, public hashedPlaintext?: string,
              public metadata?: Metadata) {}
}
