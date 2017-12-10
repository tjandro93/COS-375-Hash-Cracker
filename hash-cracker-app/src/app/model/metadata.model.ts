/*
Model of Metadata which is attached to an Lm Hash
 */

export class Metadata {
  constructor(public id?: number,
              public timesRequested?: number,
              public instantFound?: string,
              public secondsToFind?: number,
              public hasBeenFound?: boolean) {}
}
