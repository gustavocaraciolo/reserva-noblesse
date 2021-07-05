import { IApartamento } from '@/shared/model/apartamento.model';

export interface ITorre {
  id?: number;
  numero?: number | null;
  apartamentos?: IApartamento[] | null;
}

export class Torre implements ITorre {
  constructor(public id?: number, public numero?: number | null, public apartamentos?: IApartamento[] | null) {}
}
