import { IOrder } from '@/shared/model/order.model';
import { ICustomer } from '@/shared/model/customer.model';

export interface IAddress {
  id?: number;
  street1?: string | null;
  street2?: string | null;
  unit?: string | null;
  zipCode?: string | null;
  phoneNumber?: string | null;
  longitude?: number | null;
  latitude?: number | null;
  order?: IOrder | null;
  customer?: ICustomer | null;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public street1?: string | null,
    public street2?: string | null,
    public unit?: string | null,
    public zipCode?: string | null,
    public phoneNumber?: string | null,
    public longitude?: number | null,
    public latitude?: number | null,
    public order?: IOrder | null,
    public customer?: ICustomer | null
  ) {}
}
