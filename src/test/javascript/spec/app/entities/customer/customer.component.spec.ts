/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CustomerComponent from '@/entities/customer/customer.vue';
import CustomerClass from '@/entities/customer/customer.component';
import CustomerService from '@/entities/customer/customer.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Customer Management Component', () => {
    let wrapper: Wrapper<CustomerClass>;
    let comp: CustomerClass;
    let customerServiceStub: SinonStubbedInstance<CustomerService>;

    beforeEach(() => {
      customerServiceStub = sinon.createStubInstance<CustomerService>(CustomerService);
      customerServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CustomerClass>(CustomerComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          customerService: () => customerServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      customerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCustomers();
      await comp.$nextTick();

      // THEN
      expect(customerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.customers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      customerServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(customerServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCustomer();
      await comp.$nextTick();

      // THEN
      expect(customerServiceStub.delete.called).toBeTruthy();
      expect(customerServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
