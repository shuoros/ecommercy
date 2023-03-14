import { IProduct } from '@/shared/model/product.model';

export interface IPicture {
  id?: number;
  path?: string | null;
  product?: IProduct | null;
}

export class Picture implements IPicture {
  constructor(public id?: number, public path?: string | null, public product?: IProduct | null) {}
}
