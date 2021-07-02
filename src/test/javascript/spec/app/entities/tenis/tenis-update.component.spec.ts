/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import TenisUpdateComponent from '@/entities/tenis/tenis-update.vue';
import TenisClass from '@/entities/tenis/tenis-update.component';
import TenisService from '@/entities/tenis/tenis.service';

import UserService from '@/admin/user-management/user-management.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Tenis Management Update Component', () => {
    let wrapper: Wrapper<TenisClass>;
    let comp: TenisClass;
    let tenisServiceStub: SinonStubbedInstance<TenisService>;

    beforeEach(() => {
      tenisServiceStub = sinon.createStubInstance<TenisService>(TenisService);

      wrapper = shallowMount<TenisClass>(TenisUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          tenisService: () => tenisServiceStub,

          userService: () => new UserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.tenis = entity;
        tenisServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tenisServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.tenis = entity;
        tenisServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tenisServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTenis = { id: 123 };
        tenisServiceStub.find.resolves(foundTenis);
        tenisServiceStub.retrieve.resolves([foundTenis]);

        // WHEN
        comp.beforeRouteEnter({ params: { tenisId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tenis).toBe(foundTenis);
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
