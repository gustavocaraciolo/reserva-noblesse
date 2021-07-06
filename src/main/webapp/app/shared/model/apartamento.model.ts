import { IUser } from '@/shared/model/user.model';
import { ITorre } from '@/shared/model/torre.model';

export interface IApartamento {
  id?: number;
  numero?: number | null;
  user?: IUser | null;
  torre?: ITorre | null;
}

export class Apartamento implements IApartamento {
  constructor(public id?: number, public numero?: number | null, public user?: IUser | null, public torre?: ITorre | null) {}
}
