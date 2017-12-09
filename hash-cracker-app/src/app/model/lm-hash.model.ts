import {Metadata} from "./metadata.model";

export class LmHash {
  constructor(public id?: number, public hashedPlaintext?: string,
              public metadata?: Metadata) {}
}
