import { ITorre } from '@/shared/model/torre.model';
import { IUser } from '@/shared/model/user.model';

export interface IApartamento {
  id?: number;
  numero?: number | null;
  torre?: ITorre | null;
  users?: IUser[] | null;
}

export class Apartamento implements IApartamento {
  constructor(public id?: number, public numero?: number | null, public torre?: ITorre | null, public users?: IUser[] | null) {}
}
