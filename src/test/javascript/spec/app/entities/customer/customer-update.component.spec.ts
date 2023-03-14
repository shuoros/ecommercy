/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CustomerUpdateComponent from '@/entities/customer/customer-update.vue';
import CustomerClass from '@/entities/customer/customer-update.component';
import CustomerService from '@/entities/customer/customer.service';

import UserService from '@/entities/user/user.service';

import BasketService from '@/entities/basket/basket.service';

import CommentService from '@/entities/comment/comment.service';

import AddressService from '@/entities/address/address.service';

import CouponService from '@/entities/coupon/coupon.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Customer Management Update Component', () => {
    let wrapper: Wrapper<CustomerClass>;
    let comp: CustomerClass;
    let customerServiceStub: SinonStubbedInstance<CustomerService>;

    beforeEach(() => {
      customerServiceStub = sinon.createStubInstance<CustomerService>(CustomerService);

      wrapper = shallowMount<CustomerClass>(CustomerUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          customerService: () => customerServiceStub,
          alertService: () => new AlertService(),

          userService: () => new UserService(),

          basketService: () =>
            sinon.createStubInstance<BasketService>(BasketService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          commentService: () =>
            sinon.createStubInstance<CommentService>(CommentService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          addressService: () =>
            sinon.createStubInstance<AddressService>(AddressService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          couponService: () =>
            sinon.createStubInstance<CouponService>(CouponService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.customer = entity;
        customerServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(customerServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.customer = entity;
        customerServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(customerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCustomer = { id: 123 };
        customerServiceStub.find.resolves(foundCustomer);
        customerServiceStub.retrieve.resolves([foundCustomer]);

        // WHEN
        comp.beforeRouteEnter({ params: { customerId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.customer).toBe(foundCustomer);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
